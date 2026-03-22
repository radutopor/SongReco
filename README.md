# Test Multiplatform App

This is a test project, created as a playground to explore some front-end architectural patterns in a multiplatform app project.
Here are some considerations regarding this project:

## App state

The biggest subject I wanted to explore was state management in the context of a single-Activity multiplatform app with a view powered
entirely by Compose. Seeing that Compose is inherently descriptive and promotes stateless UI, it has already been the case that the UI tends
to be very modular and hierarchical. The individual Composables are usually parametrised with state objects, usually a single Kotlin data
class to keep the state cohesive. In a single-Activity app, a root Composable hierarchically describes the entire app's view.

That being said, I've been bothered by a case where the stateless and descriptive philosophy of Compose gets a bit nebulous, which is
navigation. I've felt this about both Compose Navigation itself and also some of the other navigation frameworks build for Compose that came
beforehand, where navigation state is a separte thing from UI state, maintained separately and handled by different mechanisms.

This project was my attempt at solving this conceptual issue by taking the relationship between the Compose hierarchy and its view state
objects to its logical conclusion, namely a parallel app state hierarchy. As such, one Kotlin data class (in this case `RootState`)
completely and uniquely models the state of the **entire** app, including what can be considered navigation state. This app state object
plugs into the root app view Composable. This has a few advantages, some practical and some more conceptual:

#### Cohesion

As mentioned, the app state class is the sole representational model for the app, representing both its UI and any meta-visual state.
Conceptualising the app state as a single entity helps avoid invalid states. Also, being a modular composition of substates `data` classes,
it makes it easy to navigate down to any relevant piece of state and see how it models the corresponding UI.

#### Modularity

The app state class parallels Compose by being highly composite and deeply hierarchical. It composes substates which compose other
substates, narrowing in scope, down to the leaf level where each one describes an atomic area of the app's UI and behavior. This aggregation
is achieved across Gradle modules, allowing for maximum independence between distinct business domains, while maintaining cohesion.

#### Single source of truth

One StateFlow provides a stream of app state objects to the root Composable, and this is the only source for what the app presents and how
it behaves as a whole; there are no other mechanisms through which the app's view can change.

#### Immutability

Being a Kotlin data class, the app state objects emitted by the StateFlow are immutable. This means no temporary invalid states, and the
only way to change any part of the current state is to recompose and emit a new app state object. This need for recomposition mirrors
Compose concepts.

#### Deterministic

App view changes are governed by the sequential emissions of the app state StateFlow, coordinated on the Main thread, which helps with
avoiding race conditions at view level.

#### Debugging

Each app state emission acts as a serializable representation of the app at a given point, and sequential emissions describe the
evolution of the app in time. By having a single point of traffic for state updates, it's easy to add debug logging to get a comprehensive
and timestamped app state log.

#### Navigation

Lastly, this approach allows for very flexible substate configurations, and full-screen navigation is just one specific strategy for
composing substates. In this project, the root state composes various feature substates in a `SubstateStack` container (which gives
access to operators handling substate lifecycles), while navigation requests from the substates are modeled with `UpwardRequest`.
This particular implementation involves both back-stack and tab navigation inside a specific section of the parent UI. But it's equally
possible to have multiple back-stacks in the same UI, nested substates, or some other complex substate composition logic - as
long as there is also a robust strategy for managing the lifecycle of the substates.


<img src="readme_diag_appstate.svg" width="800" />

## Project structure

As an exercise, I've structured the project with scalability, dependency management and build times in mind, trying to keep the
perspective of a much larger production project. Some key aspect I want to highlight:

### Modules

There are two groups of modules: `core` and `feature`, with an additional `:composeApp` shell module assembling the final platform-specific
build artifacts.

The `core` modules are single-responsibility, cross-domain project libraries, and meant to be depended on by the feature modules as needed.

The `feature` modules represent vertically siloed business domains, with individual domain models and logic.

### Framework code

My aim was to keep any sort of framework abstractions to a minimum and allow feature modules the flexibility needed to best
implement their individual concerns, while also keeping a good level of consistency when solving common problems.

The `:core:arch` module contains minimal, lightweight and unopinionated utilities offering standardised solutions for common
architectural concerns, like composing a stack of substates or modeling a substate request to a parent. These are meant to be used if and
when needed, and are designed to be independent of each other, useful in different contexts, and not force feature implementations into
overly-specific design patterns.

On a related note, I've also tried to be equally conservative and selective with any external library dependencies, and within any feature
implementation I've generally avoided relying on too much code which is not owned by the feature itself.

### Dependency granularity

Each feature module has an `api` and an `impl` submodule - the former only contains the public models needed to interface with the feature
(like launch parameters), while the latter contains the actual implementation. The idea is that a parent who wants to incorporate a specific
feature would depend on its `impl` module, while sibling features who just want to interface with it would only depend on
its minimal `api` module. This means that features can reference each other in terms of exchanged data, but not depend on each other's
implementation. Which in turns means better decoupling, more formalised and robust integrations, but also faster build times since
rebuilding a feature `impl` does not recursively trigger rebuilding the dependents of its `api`.

An example of this in the project is the `:feature:list:impl` module only depending on the `:feature:details:api` module.

#### Test dependencies

Under the same philosophy, reusable test doubles for the `webapi` module are separated in the `:core:webapi:testdoubles` submodule, to be
exclusively depended on in the `commonTest` source set of features who need them for their unit tests. This type of granularity helps
to keep build times low and test fixtures out of published app artifacts.

### Common code

I also wanted to touch on the importance of keeping code platform-agnostic. All modules are multiplatform modules, and when needed, I've
made use of `expected`/`actual` to provide separate implementations in platform-specific source-sets, but those are kept to an absolute
minimum.

## Redux

Within the individual feature modules, I've used a software design pattern structured around the concepts of Redux.

### View, State and Store

There are of course the feature View Composable (e.g. `ListUi`) which is meant to be composed in a parent Composable, and the feature State
class (e.g. `ListState`) which is similarly meant to be composed in a parent State class. An observable feature State is held and updated
in a Store class (e.g. `ListStore`), exposing it to parents who wish to compose it.

### Reducer

The Reducer of a feature (e.g. `ListReducer`) provides the pure function `reduce()`, which takes an existing State as the receiver, an
Action as the argument (e.g. `ListAction`), and returns a new State. The Reducer accounts for all the state transitional logic. By
convention, it also provides the initial value for the state Store.

#### Unidirectional

Of note is that all explicit data flow is unidirectional - state is produced by the Reducer and consumed by the View. This decouples the
producer and the consumer, and is achieved by hoisting UI callbacks into the State class, which are also considered part of the UI state.
In order for the pure Reducer to construct these UI callbacks, an "Action sink" is provided by the Store in the form of a `(Action) -> Unit`
lambda.

### Launcher

Additionally, some features may require launching side effects on certain Actions, like retrieving data asynchronously from a repository.
This done by a separate Launcher component (e.g. `ListLauncher`), which parallels the Reducer by also having a central function `launch()`
which is invoked on a new Action, and being parametrised by the Store with an Action sink.

### Repository

If a feature needs it, a Repository layer is implemented (e.g. `ListRepository`), which provides business logic abstractions (e.g.
`ListModel`) built from a raw data source (e.g. `WebApi`). It can delegate mapping to and from data source entities to a separate Mapper
(e.g. `ListWebApiMapper`).

## Other notes

### Dependency injection

If a runtime dependency is static within the scope of a component and reused throughout, I've favoured injecting it in the
constructor, as opposed to providing it repeatedly as a method argument. To enable this, I've made use of Factories when needed (e.g.
`TrendingLauncherFactory`) to aggregate runtime dependencies (provided programmatically) and compile-time dependencies (provided through
automatic DI) when constructing the component.

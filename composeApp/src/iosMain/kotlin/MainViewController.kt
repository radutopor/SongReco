import androidx.compose.ui.window.ComposeUIViewController
import com.radutopor.songreco.AppUi
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController =
    ComposeUIViewController { AppUi() }
package com.radutopor.songreco.core.webapi

import com.radutopor.songreco.core.webapi.models.responses.ErrorResponse

class WebApiException(val errorResponse: ErrorResponse) : Exception(errorResponse.message)
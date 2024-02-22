package com.tools.assetmanagement.model.response

data class TagResult<V, T>(
    var tagData: V,
    var data: T? = null
)
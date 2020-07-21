package org.shao.mvvmsample.model

data class RandomCatFactModel(
    var id: String?,
    // Actually "text" is the only meaningful one for this App.
    // Others are just to show how Data Model works.
    var text: String?,
    var updatedAt: String?,
    var createdAt: String?,
    var user: String?
)
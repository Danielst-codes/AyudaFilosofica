package com.example.ayudafilosofica.data.remote.dto.response

data class GenerateContentResponse(
    val candidates : List<CandidateDTO>,
    val usageMetadata: UsageMetadataDTO? = null
)

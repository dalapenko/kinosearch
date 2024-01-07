package tech.dalapenko.data.core.mapper

interface DboMapper<DBO, MODEL> {

    fun mapToModel(dbo: DBO): MODEL
    fun mapToDbo(model: MODEL): DBO
}
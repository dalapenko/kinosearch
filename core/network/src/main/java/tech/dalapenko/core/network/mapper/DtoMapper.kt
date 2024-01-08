package tech.dalapenko.core.network.mapper

interface DtoMapper<DTO, MODEL> {

    fun mapToModel(dto: DTO): MODEL
    fun mapToDto(model: MODEL): DTO
}
package br.unipar.pos.meuead.application.configs

import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DTOMapperConfig {

    @Bean
    fun mapper(): DTOMapper = DTOMapperImpl()

}

interface DTOMapper {
    fun <D> map(source: Any, destinationType: Class<D>): D
}

private class DTOMapperImpl : DTOMapper {

    private val modelMapper: ModelMapper = ModelMapper()
    override fun <D> map(source: Any, destinationType: Class<D>): D = modelMapper.map(source, destinationType)

}

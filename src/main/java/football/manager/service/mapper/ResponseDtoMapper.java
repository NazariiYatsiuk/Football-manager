package football.manager.service.mapper;

public interface ResponseDtoMapper<T, D> {
    D toDto(T t);
}

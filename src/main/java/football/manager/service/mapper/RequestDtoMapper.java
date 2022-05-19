package football.manager.service.mapper;

public interface RequestDtoMapper<D, T> {
    T toModel(D d);
}

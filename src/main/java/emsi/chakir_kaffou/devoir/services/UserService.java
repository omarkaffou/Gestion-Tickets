package emsi.chakir_kaffou.devoir.services;

import java.util.List;

public interface UserService<T> {
    T add(T t);

    T update(T t);

    T delete(T t);

    T findById(int id);

    List<T> findAll();

    List<T> findByRole(String role);

    T findByEmail(String email);
}

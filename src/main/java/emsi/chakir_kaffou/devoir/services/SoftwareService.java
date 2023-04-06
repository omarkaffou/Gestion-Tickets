package emsi.chakir_kaffou.devoir.services;

import java.util.List;

import emsi.chakir_kaffou.devoir.models.Software;

public interface SoftwareService {
    Software add(Software software);

    Software update(Software software);

    Software delete(Software software);

    Software findById(int id);

    List<Software> findAll();
}

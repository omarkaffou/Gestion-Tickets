package emsi.chakir_kaffou.devoir.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import emsi.chakir_kaffou.devoir.models.Software;
import emsi.chakir_kaffou.devoir.repo.SoftwareRepo;

@Service
public class SoftwareServiceImpl implements SoftwareService {

    @Autowired
    private SoftwareRepo softwareRepo;

    @Override
    public Software add(Software software) {
        return softwareRepo.save(software);
    }

    @Override
    public Software update(Software software) {
        if (softwareRepo.existsById(software.getId())) {
            return softwareRepo.save(software);
        }
        return null;
    }

    @Override
    public Software delete(Software software) {
        if (softwareRepo.existsById(software.getId())) {
            softwareRepo.delete(software);
            return software;
        }
        return null;
    }

    @Override
    public Software findById(int id) {
        return softwareRepo.findById(id).orElse(null);
    }

    @Override
    public List<Software> findAll() {
        return softwareRepo.findAll();
    }
    
}

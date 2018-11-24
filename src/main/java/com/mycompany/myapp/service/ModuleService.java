package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Module;
import com.mycompany.myapp.repository.ModuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Module.
 */
@Service
@Transactional
public class ModuleService {

    private final Logger log = LoggerFactory.getLogger(ModuleService.class);

    private final ModuleRepository moduleRepository;

    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    /**
     * Save a module.
     *
     * @param module the entity to save
     * @return the persisted entity
     */
    public Module save(Module module) {
        log.debug("Request to save Module : {}", module);
        return moduleRepository.save(module);
    }

    /**
     * Get all the modules.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Module> findAll() {
        log.debug("Request to get all Modules");
        return moduleRepository.findAll();
    }


    /**
     * Get one module by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Module> findOne(Long id) {
        log.debug("Request to get Module : {}", id);
        return moduleRepository.findById(id);
    }

    /**
     * Delete the module by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Module : {}", id);
        moduleRepository.deleteById(id);
    }
}

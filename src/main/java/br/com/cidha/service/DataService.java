package br.com.cidha.service;

import br.com.cidha.domain.Data;
import br.com.cidha.repository.DataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Data}.
 */
@Service
@Transactional
public class DataService {

    private final Logger log = LoggerFactory.getLogger(DataService.class);

    private final DataRepository dataRepository;

    public DataService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    /**
     * Save a data.
     *
     * @param data the entity to save.
     * @return the persisted entity.
     */
    public Data save(Data data) {
        log.debug("Request to save Data : {}", data);
        return dataRepository.save(data);
    }

    /**
     * Get all the data.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Data> findAll(Pageable pageable) {
        log.debug("Request to get all Data");
        return dataRepository.findAll(pageable);
    }


    /**
     * Get one data by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Data> findOne(Long id) {
        log.debug("Request to get Data : {}", id);
        return dataRepository.findById(id);
    }

    /**
     * Delete the data by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Data : {}", id);
        dataRepository.deleteById(id);
    }
}

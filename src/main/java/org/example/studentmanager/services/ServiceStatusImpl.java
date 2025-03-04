package org.example.studentmanager.services;

import org.example.studentmanager.model.Status;
import org.example.studentmanager.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceStatusImpl implements StatusService{

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public void save(Status status) {
        statusRepository.save(status);

    }

    @Override
    public List<Status> findAll() {
        return statusRepository.findAll();
    }
}

package org.example.studentmanager.services;


import org.example.studentmanager.model.Status;

import java.util.List;

public interface StatusService {

    public void save(Status status);

    public List<Status> findAll();
}

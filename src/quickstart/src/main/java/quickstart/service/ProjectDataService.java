package quickstart.service;

import java.util.List;

import quickstart.model.ProjectData;

public interface ProjectDataService {

    public List<ProjectData> findAll();

    public void save(ProjectData projectData);

    public void remove(int id);

    public ProjectData find(int id);
}

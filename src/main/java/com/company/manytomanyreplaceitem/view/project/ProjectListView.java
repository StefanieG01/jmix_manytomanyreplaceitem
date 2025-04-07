package com.company.manytomanyreplaceitem.view.project;

import com.company.manytomanyreplaceitem.entity.Project;
import com.company.manytomanyreplaceitem.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "projects", layout = MainView.class)
@ViewController(id = "Project.list")
@ViewDescriptor(path = "project-list-view.xml")
@LookupComponent("projectsDataGrid")
@DialogMode(width = "64em")
public class ProjectListView extends StandardListView<Project>
{}
package org.example.studentmanager.views;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.example.studentmanager.model.Status;
import org.example.studentmanager.model.Student;

import java.util.ArrayList;
import java.util.List;

@PageTitle(value = "Home")
@Route(value = "")
public class MainView extends VerticalLayout {

    private Grid<Student> studentGrid = new Grid<>();


    public MainView() {

        studentGrid = new Grid<>(Student.class);

        setSizeFull();
        setAlignItems(Alignment.CENTER);

        List<Student> students = new ArrayList<>();
        students.add(new Student("Nuria",23,80298,"Spain",new Status("Active")));
        studentGrid.setItems(students);

        add(studentGrid);


    }
}

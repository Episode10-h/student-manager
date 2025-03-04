package org.example.studentmanager.views;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.example.studentmanager.model.Status;
import org.example.studentmanager.model.Student;
import org.example.studentmanager.services.StudentService;

import java.util.ArrayList;
import java.util.List;

@PageTitle(value = "Home")
@Route(value = "/MainView")
public class MainView extends VerticalLayout {

    private final StudentService studentService;

    private Grid<Student> studentGrid = new Grid<>();

    private LogoLayout logoLayout;

    private TextField filterField;

    public MainView(StudentService studentService) {

        this.studentService = studentService;

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        createFieldVariables();
        loadStudents();
        configureGrid();

        add(logoLayout, createToolBar() , studentGrid);
    }

    private Component createToolBar() {
        filterField.setPlaceholder("Filter by name");
        filterField.setClearButtonVisible(true);
        filterField.setValueChangeMode(ValueChangeMode.LAZY);
        filterField.addValueChangeListener(e -> updateStudents());

        Button addStudent = new Button("Add Student");
        Button removeStudent = new Button("Remove Student");

        addStudent.addClickListener(e -> {
           getUI().ifPresent(ui -> ui.navigate("/add-student"));
        });

        removeStudent.addClickListener(e -> {
           getUI().ifPresent(ui -> ui.navigate("/remove-student"));
        });



                return new HorizontalLayout(filterField, addStudent, removeStudent);
    }

    private void updateStudents() {
        studentGrid.setItems(studentService.find(filterField.getValue()));

    }

    private void configureGrid() {
        studentGrid.setSizeFull();
        studentGrid.setColumns("country", "zipCode");
        studentGrid.addColumn(s -> s.getName()).setHeader("Name");

        studentGrid.addComponentColumn(s -> {
            Icon icon;

            if(s.getStatus() == null){
                s.setStatus(new Status("ABSOLVED"));
            }


            if (s.getStatus().getName().equals("ACTIVE")) {
                icon = VaadinIcon.CHECK.create();
                icon.setColor("green");
            } else if (s.getStatus().getName().equals("ABSOLVED")) {
                icon = VaadinIcon.CLOSE_CIRCLE.create();
                icon.setColor("red");
            } else {
                icon = VaadinIcon.CIRCLE.create();
                icon.setColor("orange");
            }
            return icon;
        }).setHeader("Status");
    }

    private void loadStudents() {
        List<Student> students = studentService.findAll();
        studentGrid.setItems(students);
    }

    private void createFieldVariables() {
        this.logoLayout = new LogoLayout();
        this.studentGrid = new Grid<>(Student.class);
        this.filterField = new TextField();
    }
}

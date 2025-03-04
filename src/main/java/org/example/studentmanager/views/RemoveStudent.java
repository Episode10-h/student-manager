package org.example.studentmanager.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.data.selection.SelectionListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.example.studentmanager.model.Status;
import org.example.studentmanager.model.Student;
import org.example.studentmanager.services.StudentService;

import java.util.Set;

@PageTitle("Remove Students")
@Route(value = "remove-student")
public class RemoveStudent extends VerticalLayout implements SelectionListener {

    private final StudentService studentService;
    private Grid<Student> studentGrid;
    private Button removeButton;
    private Button cancelButton;
    private Set<Student> selectedStudents;


    public RemoveStudent(StudentService studentService) {
        this.studentService = studentService;

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        createFieldVariables();
        configureGrid();

        add(studentGrid, createButtonLayout());

        loadStudents();
    }

    private void loadStudents() {
        studentGrid.setItems(studentService.findAll());
        studentGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        studentGrid.addSelectionListener(this);

    }

    private Component createButtonLayout() {
            removeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

            removeButton.addClickListener(e -> removeStudent());
            cancelButton.addClickListener(e -> closeView());
        return new HorizontalLayout(removeButton,cancelButton);
    }

    private void removeStudent() {

        selectedStudents.forEach(studentService::remove);
        Notification notification = Notification.
                show("Student(s) removed succesfully");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        studentGrid.setItems(studentService.findAll());
    }

    private void closeView() {
    getUI().ifPresent(ui -> ui.navigate("/MainView"));

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

    private void createFieldVariables() {
         studentGrid = new Grid<>(Student.class);
         removeButton = new Button("Remove");
         cancelButton = new Button("Cancel");

    }


    @Override
    public void selectionChange(SelectionEvent selectionEvent) {
        selectedStudents = selectionEvent.getAllSelectedItems();
    }
}

package org.example.studentmanager.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.example.studentmanager.model.Status;
import org.example.studentmanager.model.Student;
import org.example.studentmanager.services.StatusService;
import org.example.studentmanager.services.StudentService;

import java.util.List;

@PageTitle("Add Students")
@Route(value = "add-student")
public class AddStudentView extends VerticalLayout {

    private final StatusService statusService;
    private final StudentService studentService;

    private TextField name;
    private TextField zipCode;
    private TextField age;
    private TextField country;
    private ComboBox<Status> status;
    private LogoLayout image;

    private Button save;
    private Button close;

    private Student student;
    private Binder<Student> binder;


    public AddStudentView(StatusService statusService, StudentService studentService) {
        this.statusService = statusService;
        this.studentService = studentService;

        setAlignItems(Alignment.CENTER);

        createVariables();
        createStatus();
        createBinder();
        add(image);
        add(createFormLayout());



    }

    private void createBinder() {
        student = new Student();
        binder = new BeanValidationBinder<>(Student.class);
        binder.bindInstanceFields(this);
    }

    private void createStatus() {
        List<Status> statuses = statusService.findAll();


        status.setItems(statuses);
        status.setValue(statuses.get(0));
        status.setItemLabelGenerator(Status::getName);
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(name, zipCode, age, country, status, createButtons());
        formLayout.setColspan(image, 2);
        formLayout.setColspan(name, 2);

        return formLayout;
    }

    private Component createButtons() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        close.addClickListener(e -> closeView());
        save.addClickListener(e -> saveStudent());
        
        return new HorizontalLayout(save, close);
    }

    private void saveStudent() {
        try {
            binder.writeBean(student);
            studentService.save(student);
            Notification notification = new Notification().show("Student saved successfully...");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.setPosition(Notification.Position.TOP_CENTER);

            clearFields();
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        student = new Student();
        status.setValue(statusService.findAll().get(0));
        binder.getFields().forEach(HasValue::clear);
    }

    private void closeView() {
        getUI().ifPresent(ui -> ui.navigate("/MainView"));
    }

    private void createVariables() {
        age = new TextField("Age");
        name = new TextField("First name");
        zipCode = new TextField("Zip code");
        country = new TextField("Country");
        status = new ComboBox<>("Status");
        image = new LogoLayout();
        save = new Button("Save");
        close = new Button("Close");
    }
}

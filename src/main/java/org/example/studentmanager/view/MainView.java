package org.example.studentmanager.view;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle(value = "Home")
@Route(value = "")
public class MainView extends VerticalLayout {

    public void main(String[] args) {
        add(new Button("Click me"));
    }
}

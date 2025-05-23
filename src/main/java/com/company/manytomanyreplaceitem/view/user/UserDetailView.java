package com.company.manytomanyreplaceitem.view.user;

import com.company.manytomanyreplaceitem.entity.Language;
import com.company.manytomanyreplaceitem.entity.Project;
import com.company.manytomanyreplaceitem.entity.User;
import com.company.manytomanyreplaceitem.view.language.LanguageListView;
import com.company.manytomanyreplaceitem.view.main.MainView;
import com.company.manytomanyreplaceitem.view.project.ProjectListView;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.EntityStates;
import io.jmix.core.MessageTools;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.model.CollectionPropertyContainer;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

@Route(value = "users/:id", layout = MainView.class)
@ViewController(id = "User.detail")
@ViewDescriptor(path = "user-detail-view.xml")
@EditedEntityContainer("userDc")
public class UserDetailView extends StandardDetailView<User>
{

    @ViewComponent
    private TypedTextField<String> usernameField;
    @ViewComponent
    private PasswordField passwordField;
    @ViewComponent
    private PasswordField confirmPasswordField;
    @ViewComponent
    private ComboBox<String> timeZoneField;
    @ViewComponent
    private MessageBundle messageBundle;
    @Autowired
    private MessageTools messageTools;
    @Autowired
    private Notifications notifications;

    @Autowired
    private EntityStates entityStates;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @ViewComponent
    private CollectionPropertyContainer<Project> projectsDc;
    @Autowired
    private DataManager dataManager;
    @ViewComponent
    private DataContext dataContext;
    @ViewComponent
    private CollectionLoader<Language> languagesDl;
    @ViewComponent
    private EntityComboBox<Language> languageField;
    @ViewComponent
    private CollectionContainer<Language> languagesDc;

    @Subscribe
    public void onInit(final InitEvent event)
    {
        timeZoneField.setItems(List.of(TimeZone.getAvailableIDs()));
    }

    @Subscribe
    public void onInitEntity(final InitEntityEvent<User> event)
    {
        usernameField.setReadOnly(false);
        passwordField.setVisible(true);
        confirmPasswordField.setVisible(true);
    }

    @Subscribe
    public void onReady(final ReadyEvent event)
    {
        if(entityStates.isNew(getEditedEntity()))
        {
            usernameField.focus();
        }
    }

    @Subscribe
    public void onValidation(final ValidationEvent event)
    {
        if(entityStates.isNew(getEditedEntity()) &&
                !Objects.equals(passwordField.getValue(), confirmPasswordField.getValue()))
        {
            event.getErrors()
                    .add(messageBundle.getMessage("passwordsDoNotMatch"));
        }
    }

    @Subscribe
    public void onBeforeSave(final BeforeSaveEvent event)
    {
        if(entityStates.isNew(getEditedEntity()))
        {
            getEditedEntity().setPassword(passwordEncoder.encode(passwordField.getValue()));

            String entityCaption = messageTools.getEntityCaption(getEditedEntityContainer().getEntityMetaClass());
            notifications.create(messageBundle.formatMessage("noAssignedRolesNotification", entityCaption))
                    .withType(Notifications.Type.WARNING)
                    .withPosition(Notification.Position.TOP_END)
                    .show();
        }
    }

    @Install(to = "projectsDataGrid.addAction", subject = "afterCloseHandler")
    private void projectsDataGridAddActionAfterCloseHandler(
            final DialogWindow.AfterCloseEvent<ProjectListView> afterCloseEvent)
    {
        // solution from AI Assistant --> shows unsaved changes dialog
/*        projectsDc.getMutableItems()
                .clear();
        projectsDc.getMutableItems()
                .addAll(dataManager.load(User.class)
                        .id(getEditedEntity().getId())
                        .one()
                        .getProjects());*/

        // does not show unsaved changes dialog, but also no update on dataGrid entry
        List<Project> projects = projectsDc.getItems();

        for(Project project : projects)
        {
            projectsDc.replaceItem(project);
        }
    }

    @Install(to = "languageField.entityLookup", subject = "afterCloseHandler")
    private void languageFieldEntityLookupAfterCloseHandler(final DialogWindow.AfterCloseEvent<LanguageListView> afterCloseEvent)
    {
        // reload all items for dropdown --> there might be new entities/changed entities in lookup
        languagesDl.load();
    }
}
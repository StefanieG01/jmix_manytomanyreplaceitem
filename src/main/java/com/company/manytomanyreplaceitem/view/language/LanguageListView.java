package com.company.manytomanyreplaceitem.view.language;

import com.company.manytomanyreplaceitem.entity.Language;
import com.company.manytomanyreplaceitem.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "languages", layout = MainView.class)
@ViewController(id = "Language_.list")
@ViewDescriptor(path = "language-list-view.xml")
@LookupComponent("languagesDataGrid")
@DialogMode(width = "64em")
public class LanguageListView extends StandardListView<Language>
{}
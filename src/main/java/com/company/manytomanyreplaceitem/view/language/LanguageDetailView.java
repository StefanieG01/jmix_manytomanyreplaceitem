package com.company.manytomanyreplaceitem.view.language;

import com.company.manytomanyreplaceitem.entity.Language;
import com.company.manytomanyreplaceitem.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "languages/:id", layout = MainView.class)
@ViewController(id = "Language_.detail")
@ViewDescriptor(path = "language-detail-view.xml")
@EditedEntityContainer("languageDc")
public class LanguageDetailView extends StandardDetailView<Language>
{}
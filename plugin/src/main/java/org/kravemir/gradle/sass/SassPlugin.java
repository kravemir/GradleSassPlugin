package org.kravemir.gradle.sass;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class SassPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.getExtensions().add("sass", project.container(
                SassCompileTask.class,
                name -> {
                    SassCompileTask task = project.getTasks().create(name + "Sass", SassCompileTask.class);
                    task.setGroup("sass");
                    return task;
                }
        ));
    }
}

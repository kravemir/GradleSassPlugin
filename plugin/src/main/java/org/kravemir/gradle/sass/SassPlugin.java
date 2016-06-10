package org.kravemir.gradle.sass;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * Sass Compiler Plugin. The plugin creates "sass" container extension,
 * which is used to easily define {@link SassCompileTask}(s).
 *
 * @author Miroslav Kravec
 */
public class SassPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.getExtensions().add("sass", project.container(
                SassCompileTask.class,
                name -> project.getTasks().create(name + "Sass", SassCompileTask.class, task -> task.setGroup("sass"))
        ));
    }
}

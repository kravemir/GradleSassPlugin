package org.kravemir.gradle.sass;

import org.gradle.api.DomainObjectCollection;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class SassPlugin implements Plugin<Project>{
    @Override
    public void apply(Project project) {
        DomainObjectCollection<SassSourceSet> sassSourceSets = project.container(SassSourceSet.class);
        project.getExtensions().add("sass", sassSourceSets);

        project.afterEvaluate(p -> sassSourceSets.all(sassPack -> {
            project.getTasks().create(sassPack.getName() + "Sass", SassCompileTask.class, t -> {
                t.setGroup("sass");
                t.setSrcDir(sassPack.getSrcDir());
                t.setOutDir(sassPack.getOutDir());
                t.setInclude(sassPack.getInclude());
                t.setExclude(sassPack.getExclude());
            });
        }));
    }
}

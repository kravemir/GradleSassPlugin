package org.kravemir.gradle.sass;

import org.gradle.api.DomainObjectCollection;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class SassPlugin implements Plugin<Project>{
    @Override
    public void apply(Project project) {
        DomainObjectCollection<SassBuildConfiguration> sassBuildConfigurations = project.container(SassBuildConfiguration.class);
        project.getExtensions().add("sass", sassBuildConfigurations);

        project.afterEvaluate(p -> sassBuildConfigurations.all(build -> {
            project.getTasks().create(build.getName() + "Sass", SassCompileTask.class, t -> {
                t.setGroup("sass");
                t.setSrcDir(build.getSrcDir());
                t.setOutDir(build.getOutDir());
                t.setInclude(build.getInclude());
                t.setExclude(build.getExclude());
                t.setMinify(build.getMinify());
            });
        }));
    }
}

package org.kravemir.gradle.sass;

import org.gradle.api.DomainObjectCollection;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginConvention;

import java.util.Collections;

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

                registerInSourceSets(project,build,t);
            });
        }));
    }

    private void registerInSourceSets(Project project, SassBuildConfiguration build, SassCompileTask task) {
        if(build.getRegisterInSourceSets() == null || build.getRegisterInSourceSets().length == 0) return;

        try {
            JavaPluginConvention javaPlugin = project.getConvention().getPlugin(JavaPluginConvention.class);
            if (javaPlugin == null) {
                throw new GradleException("You must apply the java plugin if you're using 'registerInSourceSets' functionality.");
            }

            for (String sourceSet : build.getRegisterInSourceSets()) {
                javaPlugin.getSourceSets().getByName(sourceSet).getOutput().dir(
                        Collections.singletonMap("builtBy", task),
                        build.getOutDir()
                );
            }
        } catch (Exception e) {
            throw new GradleException("You must apply the java plugin if you're using 'registerInSourceSets' functionality.");
        }
    }
}

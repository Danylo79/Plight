package dev.dankom.pm;

import dev.dankom.pm.util.PropertiesReader;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.IOException;

@Mojo(name = "info", defaultPhase = LifecyclePhase.INSTALL)
public class InfoMojo extends AbstractMojo {
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            PropertiesReader reader = new PropertiesReader("properties-pom");
            getLog().info("> > Plight Info:");
            getLog().info("> Build: " + reader.getProperty("plight.build"));
            getLog().info("> Version: " + reader.getProperty("plight.version"));
            getLog().info("> Name: " + reader.getProperty("plight.fullname"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

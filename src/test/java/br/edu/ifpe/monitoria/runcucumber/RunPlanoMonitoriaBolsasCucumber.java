package br.edu.ifpe.monitoria.runcucumber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions( features = "src\\test\\resources\\features\\planomonitoriabolsas.feature", 
        glue = "classpath:br.edu.ifpe.monitoria.step_definitions",
        monochrome = false,
        format = "progress",
        snippets = SnippetType.CAMELCASE)
public class RunPlanoMonitoriaBolsasCucumber {

}

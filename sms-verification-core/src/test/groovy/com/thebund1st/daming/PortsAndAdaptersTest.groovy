package com.thebund1st.daming

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import spock.lang.Specification

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses

class PortsAndAdaptersTest extends Specification {
    private JavaClasses importedClasses = new ClassFileImporter()
            .importPackages("com.thebund1st.daming")

    def "application should not depends on adapters"() {
        when:
        def rule = noClasses()
                .that()
                .resideInAnyPackage("..daming.application..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("..daming.adapter..")

        then:
        rule.check(this.importedClasses)
    }

    def "domain, command & event should not depends on application"() {
        when:
        def rule = noClasses()
                .that()
                .resideInAnyPackage("..daming.application.domain..",
                "..daming.application.command..",
                "..daming.application.event..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("..daming.application.commandhandling..",
                "..daming.application.validation.."
        )

        then:
        rule.check(this.importedClasses)
    }
}

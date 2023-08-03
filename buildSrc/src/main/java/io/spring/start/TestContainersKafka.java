package io.spring.start;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.starter.application.ApplicationType;
import io.micronaut.starter.application.generator.GeneratorContext;
import io.micronaut.starter.build.dependencies.Dependency;
import io.micronaut.starter.feature.Feature;
import jakarta.inject.Singleton;

@Singleton
public class TestContainersKafka implements Feature {

    public static final Dependency DEPENDENCY_TESTCONTAINERS_KAFKA = Dependency.builder()
            .groupId("org.testcontainers")
            .artifactId("kafka")
            .test()
            .build();

    @Override
    public @NonNull String getName() {
        return "testcontainers-kafka";
    }

    @Override
    public boolean supports(ApplicationType applicationType) {
        return true;
    }

    @Override
    public void apply(GeneratorContext generatorContext) {
        generatorContext.addDependency(DEPENDENCY_TESTCONTAINERS_KAFKA);
    }
}

/*
 * Copyright 2017-2022 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.guides.feature.opentelemetry;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.starter.application.generator.GeneratorContext;
import io.micronaut.starter.build.dependencies.Dependency;
import io.micronaut.starter.feature.build.maven.Profile;
import io.micronaut.starter.feature.build.maven.Property;
import io.micronaut.starter.feature.graalvm.GraalVM;
import io.micronaut.starter.options.BuildTool;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class OpenTelemetryExporterGoogleCloudTrace extends OpenTelemetryExporterFeature {

    private static final Dependency.Builder DEPENDENCY_NATIVE_IMAGE_SUPPORT = Dependency.builder()
            .groupId("com.google.cloud")
            .artifactId("native-image-support");

    private static final Profile PROFILE_GRAALVM = Profile.builder()
            .id("graalVM")
            .activationProperty(Property.builder().name("packaging").value("native-image").build())
            .dependency(DEPENDENCY_NATIVE_IMAGE_SUPPORT.build())
            .build();

    private static final Dependency DEPENDENCY_OTEL_EXPORTER_GOOGLE_CLOUD_TRACE =
            Dependency.builder()
                    .groupId("com.google.cloud.opentelemetry")
                    .artifactId("exporter-auto")
                    .compile()
                    .build();

    private static final String GOOGLE_CLOUD_TRACE = "google_cloud_trace";

    @Override
    public void apply(GeneratorContext generatorContext) {
        super.apply(generatorContext);
        if (generatorContext.getFeatures().isFeaturePresent(GraalVM.class) && generatorContext.getBuildTool().isGradle()) {
            generatorContext.addDependency(DEPENDENCY_NATIVE_IMAGE_SUPPORT.nativeImageCompileOnly().build());
        }
        if (generatorContext.getFeatures().isFeaturePresent(GraalVM.class) && generatorContext.getBuildTool() == BuildTool.MAVEN) {
            generatorContext.addProfile(PROFILE_GRAALVM);
        }
    }

    @NonNull
    protected Dependency exporterDependency() {
        return DEPENDENCY_OTEL_EXPORTER_GOOGLE_CLOUD_TRACE;
    }

    @Override
    @NonNull
    protected String exporterName() {
        return "gcp";
    }

    @Override
    @NonNull
    protected String exporterValue() {
        return GOOGLE_CLOUD_TRACE;
    }
}

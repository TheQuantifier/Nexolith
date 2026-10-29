/*
 * SPDX-License-Identifier: Apache-2.0
 */
package dev.nexolith.loader;

/**
 * An early Nexolith extension discovered through {@link java.util.ServiceLoader}.
 *
 * <p>This interface is intentionally small. It proves that Nexolith controls its own bootstrap
 * lifecycle without committing the future native mod API to this experimental contract.</p>
 */
public interface LoaderExtension {
    /** A stable, lowercase identifier used for diagnostics and duplicate detection. */
    String id();

    /** Runs before control is handed to Minecraft. */
    void initialize(LaunchContext context) throws Exception;
}

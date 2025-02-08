package nextflow.schema

import groovy.transform.PackageScope

/**
 * This class allows model specific configuration for the Schema Builder plugin
 *
 * In this plugin, the user can configure schema generation options via nextflow.config:
 *
 * schema {
 *     outputDir = 'schemas'
 * }
 *
 * We annotate this class as @PackageScope to restrict the access of their methods only to class in the
 * same package
 *
 * @author : jorge <jorge.aguilera@seqera.io>
 */
@PackageScope
class SchemaConfig {

    final private String outputDir

    SchemaConfig(Map map){
        def config = map ?: Collections.emptyMap()
        outputDir = config.outputDir ?: 'schemas'
    }

    String getOutputDir() { outputDir }
}

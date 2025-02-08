package nextflow.schema

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import groovyx.gpars.dataflow.DataflowReadChannel
import groovyx.gpars.dataflow.DataflowWriteChannel
import nextflow.Channel
import nextflow.Session
import nextflow.extension.CH
import nextflow.extension.DataflowHelper
import nextflow.plugin.extension.Factory
import nextflow.plugin.extension.Function
import nextflow.plugin.extension.Operator
import nextflow.plugin.extension.PluginExtensionPoint

/**
 * Plugin extension for schema building functionality
 *
 * @author : jorge <jorge.aguilera@seqera.io>
 */
@Slf4j
@CompileStatic
class SchemaExtension extends PluginExtensionPoint {

    /*
     * A session hold information about current execution of the script
     */
    private Session session

    /*
     * A Custom config extracted from nextflow.config under schema tag
     * nextflow.config
     * ---------------
     * docker{
     *   enabled = true
     * }
     * ...
     * schema{
     *    prefix = 'Mrs'
     * }
     */
     private SchemaConfig config

    /*
     * nf-core initializes the plugin once loaded and session is ready
     * @param session
     */
    @Override
    protected void init(Session session) {
        this.session = session
        this.config = new SchemaConfig(session.config.navigate('schema') as Map)
    }

    /*
     * {@code generateSchema} is a `producer` method and will be available to the script because:
     *
     * - it's public
     * - it returns a DataflowWriteChannel
     * - it's marked with the @Factory annotation
     *
     * The method can require arguments but it's not mandatory, it depends of the business logic of the method.
     *
     */
    @Factory
    DataflowWriteChannel generateSchema(Map processConfig) {
        final channel = CH.create()
        session.addIgniter((action) -> generateSchemaImpl(channel, processConfig))
        return channel
    }

    private void generateSchemaImpl(DataflowWriteChannel channel, Map processConfig) {
        // TODO: Implement schema generation logic
        def schema = [
            '$schema': 'https://json-schema.org/draft/2020-12/schema',
            type: 'object',
            properties: processConfig
        ]
        channel.bind(schema)
        channel.bind(Channel.STOP)
    }

    /*
    * {@code validateSchema} is a *consumer* method as it receives values from a channel to perform some logic.
    *
    * Consumer methods are introspected by nextflow-core and include into the DSL if the method:
    *
    * - it's public
    * - it returns a DataflowWriteChannel
    * - it has only one arguments of DataflowReadChannel class
    * - it's marked with the @Operator annotation 
    *
    * a consumer method needs to proportionate 2 closures:
    * - a closure to consume items (one by one)
    * - a finalizer closure
    *
    * in this case `validateSchema` will consume a message and will store it as an upper case
    */
    @Operator
    DataflowWriteChannel validateSchema(DataflowReadChannel source) {
        final target = CH.createBy(source)
        final next = { 
            // TODO: Implement schema validation logic
            target.bind("Schema validation passed for: $it".toString()) 
        }
        final done = { target.bind(Channel.STOP) }
        DataflowHelper.subscribeImpl(source, [onNext: next, onComplete: done])
        return target
    }

    /*
     * Generate a random string
     *
     * Using @Function annotation we allow this function can be imported from the pipeline script
     */
    @Function
    Map parseSchema(String schemaFile) {
        // TODO: Implement schema parsing logic
        return [:]
    }

}

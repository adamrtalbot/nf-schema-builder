/*
 * Copyright 2021, Seqera Labs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nextflow.schema

import groovy.transform.CompileStatic
import nextflow.plugin.BasePlugin
import nextflow.cli.PluginAbstractExec
import org.pf4j.PluginWrapper

/**
 * Implements the Schema Builder plugin entry point
 *
 * @author Paolo Di Tommaso <paolo.ditommaso@gmail.com>
 */
@CompileStatic
class SchemaBuilderPlugin extends BasePlugin implements PluginAbstractExec {
    SchemaBuilderPlugin(PluginWrapper wrapper) {
        super(wrapper)
    }

    @Override
    List<String> getCommands() {
        [ 'schema' ]
    }

    @Override
    int exec(String cmd, List<String> args) {
        if( cmd == 'schema' ) {
            println "Schema Builder! You gave me these arguments: ${args.join(' ')}"
            return 0
        }
        else {
            System.err.println "Invalid command: ${cmd}"
            return 1
        }
    }
}
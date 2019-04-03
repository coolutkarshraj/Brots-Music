module.exports = function (grunt) {
    // Project configuration.
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        ts: {
            build: {
                src: ["src/app.ts", "!node_modules/**/*.ts"],
                // Avoid compiling TypeScript files in node_modules
                options: {
                    "emitDecoratorMetadata": true,
                    "experimentalDecorators": true,
                    "target": "es2015",
                    "outDir": "build",

                    module: 'commonjs',
                    // To compile TypeScript using external modules like NodeJS
                    fast: 'never'
                    // You'll need to recompile all the files each time for NodeJS
                },
                tsconfig: "tsconfig.json"
            }
        },
        tslint: {
            options: {
                configuration: grunt.file.readJSON("tslint.json")
            },
            all: {
                src: ["./src/app.ts", "!node_modules/**/*.ts", "!obj/**/*.ts", "!typings/**/*.ts"]
                // avoid linting typings files and node_modules files
            }
        },
        watch: {
            scripts: {
                files: ['./src/**/**/*.ts', '!node_modules/**/*.ts'], // the watched files
                tasks: ["newer:tslint:all", "ts:build"], // the task to run
                options: {
                    spawn: false // makes the watch task faster
                }
            }
        },
        nodemon: {
            dev: {
                script: './dist/app.js'
            },
            options: {
                ignore: ['node_modules/**', 'Gruntfile.js'],
                env: {
                    PORT: '8181'
                },
                watch: ['domains', 'routes', 'models'],
                legacyWatch: true
            }
        },
        concurrent: {
            watchers: {
                tasks: ['nodemon', 'watch', "copy"],
                options: {
                    logConcurrentOutput: true
                }
            }
        },
        copy: {
            main: {
                expand: true,
                cwd: 'src/static',
                src: '**',
                dest: 'dist/static',
            },
        }
    });

    grunt.loadNpmTasks("grunt-concurrent");
    grunt.registerTask("serve", ["concurrent:watchers"]);
    grunt.loadNpmTasks("grunt-contrib-watch");
    grunt.loadNpmTasks("grunt-tslint");
    grunt.loadNpmTasks("grunt-nodemon");
    grunt.loadNpmTasks("grunt-newer");
    grunt.loadNpmTasks("grunt-ts");
    grunt.loadNpmTasks("grunt-contrib-copy");
    // Default tasks.
    grunt.registerTask('default', ["ts:build"]);
};

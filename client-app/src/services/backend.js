export default class Backend {

    constructor() {
        this.url = "http://localhost:8080/api/v1"
    }

    getUrlProjectList() {
        return this.url + "/projects"
    }

    getUrlPostProject() {
        return this.url + "/projects"
    }

    getUrlDeleteProject(name) {
        return this.url + "/projects/" + name
    }






}
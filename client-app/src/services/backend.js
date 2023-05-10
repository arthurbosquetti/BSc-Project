export default class Backend {

    constructor() {
        // Development
        this.url = "http://localhost:8080/api/v1"
        
        // // Deployment
        // this.url = "http://pm-tracker.lx-ch25.shure.com:80/api/v1"
    }

    getUrlProjectList() {
        return this.url + "/projects"
    }

    getUrlPostProject() {
        return this.url + "/projects"
    }

    getUrlGetProject(name) {
        return this.url + "/projects/" + name
    }

    getUrlPatchProject() {
        return this.url + "/projects/"
    }

    getUrlDeleteProject(name) {
        return this.url + "/projects/" + name
    }

    getUrlProjectTestDataList(name) {
        return this.url + "/projects/" + name + "/test-data"
    }

    getUrlProjectBugDataList(name) {
        return this.url + "/projects/" + name + "/bug-data"
    }

    getUrlDeleteTestDataEntry(projectName, entryDate) {
        return this.url + "/projects/" + projectName + "/test-data/" + entryDate
    }

    getUrlDeleteBugDataEntry(projectName, entryDate) {
        return this.url + "/projects/" + projectName + "/bug-data/" + entryDate
    }

    getUrlVerifyCredentials() {
        return this.url + "/verifyCredentials/"
    }
}
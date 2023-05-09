import VueRouter from 'vue-router'
import ListProject from './components/ListProject.vue'
import AddProject from './components/AddProject.vue'
import HomePage from './components/HomePage.vue'
import ReleaseNotes from './components/ReleaseNotes.vue'
import ProjectPage from './components/ProjectPage.vue'

const routes = [
    {
        path: '/home',
        component: HomePage,
        name: 'HomePage',
    },
    {
        path: '/release-notes',
        component: ReleaseNotes,
        name: 'ReleaseNotes'
    },
    {
        path: '/list-projects',
        component: ListProject,
        name: 'ListProject'
    },
    {
        path: '/add-project',
        component: AddProject,
        name: 'AddProject'
    },
    {
        path: '/project/:projectName/',
        component: ProjectPage,
        name: 'ProjectPage'
    }
]

const router = new VueRouter({
    mode: "history",
    routes: routes,
    linkActiveClass: 'active'
})

export default router
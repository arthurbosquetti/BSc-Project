import VueRouter from 'vue-router'
import ListProject from './components/ListProject.vue'
import AddProject from './components/AddProject.vue'
import DeleteProject from './components/DeleteProject.vue'
import ListTestDataEntries from './components/ListTestDataEntries.vue'
import ListBugDataEntries from './components/ListBugDataEntries.vue'
import GraphsTestDataEntries from './components/GraphsTestDataEntries.vue'
import GraphsBugDataEntries from './components/GraphsBugDataEntries.vue'
import HomePage from './components/HomePage.vue'
import ReleaseNotes from './components/ReleaseNotes.vue'

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
        path: '/delete-project',
        component: DeleteProject,
        name: 'DeleteProject'
    },
    {
        path: '/project/:projectName/test-data',
        component: ListTestDataEntries,
        name: 'ListTestDataEntries'
    },
    {
        path: '/project/:projectName/bug-data',
        component: ListBugDataEntries,
        name: 'ListBugDataEntries'
    },
    {
        path: '/project/:projectName/test-data',
        component: GraphsTestDataEntries,
        name: 'GraphsTestDataEntries'
    },
    {
        path: '/project/:projectName/bug-data',
        component: GraphsBugDataEntries,
        name: 'GraphsBugDataEntries'
    }
    
]

const router = new VueRouter({
    mode: "history",
    routes: routes,
    linkActiveClass: 'active'
})

export default router
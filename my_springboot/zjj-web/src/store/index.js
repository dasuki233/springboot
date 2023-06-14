import vue from 'vue'
import Vuex from 'vuex'
import router,{resetRouter} from "../router";
vue.use(Vuex)
import createPersistedState from 'vuex-persistedstate'

function addNewRoute(menuList) {
    {
        // console.log(menuList)
        let routes = router.options.routes

        routes.forEach(routeItem=>{
            if (routeItem.path==="/Index"){
                menuList.forEach(menu=>{
                    // console.log("menu:::::")
                    // console.log(menu)
                    if(menu.childListsss.length > 1) {
                        for (let i = 0; i < menu.childListsss.length; i++) {

                        let childRoute = {
                            path: '/' + menu.childListsss[i].menucomponent,
                            name: menu.childListsss[i].menuclick,
                            meta: {
                                title: menu.childListsss[i].menuname
                            },
                            component: () => import('../components/' + menu.childListsss[i].menucomponent)
                        }
                        routeItem.children.push(childRoute)

                        }
                    }else {
                        let childRoute = {
                            path: '/' + menu.menucomponent,
                            name: menu.menuclick,
                            meta: {
                                title: menu.menuname
                            },
                            component: () => import('../components/' + menu.menucomponent)
                        }
                        routeItem.children.push(childRoute)
                    }
                })
            }
        })
        // resetRouter()
        for (let x of routes) {
            //
            router.addRoute(x)
        }
        // resetRouter()
        // router.addRoute(routes)
        // console.log('router')
        // console.log(router)
    }
}

export default new Vuex.Store({
    state: {
        menu:[]
    },
    mutations: {
        setMenu(state, menuList) {
            state.menu = menuList
            addNewRoute(menuList)
        },
        setRoute(state, menuList){
            //添加路由
            addNewRoute(menuList)
        }
    },
    getters: {
        getMenu(state) {
            return state
        }
    },
    plugins: [createPersistedState()]
})
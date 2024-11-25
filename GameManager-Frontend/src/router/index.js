import { createRouter, createWebHistory } from 'vue-router';
import GameList from '../components/GameList.vue';
import GameDetails from '../components/GameDetails.vue';
import CreateGame from '../components/CreateGame.vue';
import UpdateGame from '../components/UpdateGame.vue';
import Login from '@/components/Login.vue';
import ApproveGames from "@/components/ApproveGames.vue";
import Cart from "@/components/Cart.vue";
<<<<<<< HEAD
import Payment from "@/components/Payment.vue";
import Category from "@/components/Category.vue";

const routes = [
  { path: '/approve-games',
    component: ApproveGames },
=======
import ApproveGames from '../components/ApproveGames.vue';

const routes = [
  { path: '/approve-games', component: ApproveGames },
>>>>>>> 34fd522 (..)
  {
    path: '/',
    name: 'GameList',
    component: GameList,
  },
  {
    path: '/games/:id',
    name: 'GameDetails',
    component: GameDetails,
    props: true,
  },
  {
    path: '/create-game',
    name: 'CreateGame',
    component: CreateGame,
  },
  {
    path: '/update-game/:id',
    name: 'UpdateGame',
    component: UpdateGame,
    props: true,
  },
  {
    path: '/cart',
    name: 'Cart',
    component: Cart,
  },
  {
    path: '/payment',
    name: 'Payment',
    component: Payment,
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/',
  },
  {
    path: '/login',
    name: 'Login',
    component: Login,
  },
  {
    path: '/',
    redirect: '/login',
  },
  {
    path: '/category',
    name: 'Categories',
    component: Category,
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;

import { routes } from '@/constants/path';
import { NotFoundPage } from '@/pages';
import { ROUTE_CHNAGE_EVENT } from '@/constants/eventName';

export default class Router {
  $target: HTMLElement;

  constructor($target: HTMLElement) {
    this.$target = $target;

    this.initRouter();
    this.route();
  }

  private findMatchedRoute() {
    return routes.find((route) => route.path.test(location.pathname));
  }

  private route() {
    const TargetPage = this.findMatchedRoute()?.element || NotFoundPage;

    const app = this.$target.cloneNode(false) as HTMLElement;
    document.body.innerHTML = '';

    app.id = 'app';
    document.body.appendChild(app);

    new TargetPage(app);
  }

  private initRouter() {
    window.addEventListener(ROUTE_CHNAGE_EVENT, (e: Event) => {
      const { nextUrl, isReplace } = (<CustomEvent>e).detail;

      if (isReplace || nextUrl === location.pathname)
        history.replaceState(null, '', nextUrl);
      else history.pushState(null, '', nextUrl);

      this.route();
    });

    window.addEventListener('popstate', () => {
      this.route();
    });
  }
}

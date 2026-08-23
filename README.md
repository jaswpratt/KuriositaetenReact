# Kuriositaeten Trivia — React Edition

A trivia quiz web app built with a **Java/Maven servlet backend** and a **React (Vite) frontend**, packaged together into a single deployable WAR. This is the React counterpart to [KuriositaetenVue](https://github.com/jaswpratt/KuriositaetenVue) — same API, same trivia source, different frontend framework.

## Live demo

- **React:** `http://srv1839678.hstgr.cloud:8080/kuriositaeten-react/`
- **Vue:** `http://srv1839678.hstgr.cloud:8080/kuriositaeten-vue/`

## Features

- Browse trivia categories pulled live from [Open Trivia DB](https://opentdb.com)
- Configure quiz parameters: number of questions, difficulty, and type (multiple choice / true-false)
- Answer questions one at a time with instant correct/incorrect feedback
- Running score tracker and a final results summary
- Graceful handling of Open Trivia DB's rate limiting (automatic retry with backoff on HTTP 429)

## Tech stack

**Backend** (`kuriositaeten-api/`)
- Java 17, Jakarta Servlet 6.0.0
- Jackson for JSON serialization
- Maven, packaged as a WAR, deployed to Apache Tomcat

**Frontend** (`kuriositaeten-react/`)
- React 19 + Vite
- Plain `fetch` for API calls, no external state management library

**Build automation**
- `frontend-maven-plugin` runs `npm install` / `npm run build` as part of the Maven lifecycle
- `maven-resources-plugin` copies the Vite build output into the servlet's `webapp` directory before packaging
- A single `mvn clean install` from `kuriositaeten-api/` builds *both* the frontend and backend and produces one deployable WAR

## Project structure

```
KuriositaetenReact/
├── kuriositaeten-api/          # Java/Maven backend
│   ├── pom.xml                 # Build automation for both backend and frontend
│   └── src/main/java/us/tn/greatsmokey/
│       ├── controller/         # CategoriesServlet, TriviaServlet
│       ├── dao/                # TriviaSourceDAO — talks to Open Trivia DB
│       └── model/               # Categories, TriviaQuestion, TriviaParams, Response
└── kuriositaeten-react/        # React/Vite frontend
    └── src/
        ├── api/                # triviaApi.js — fetch wrappers
        └── components/         # CategoryPicker, QuizControls, QuestionList, QuizQuestion
```

## API endpoints

| Endpoint | Method | Query params | Description |
|---|---|---|---|
| `/CategoriesServlet` | GET | — | Returns all available trivia categories |
| `/TriviaServlet` | GET | `count`, `category`, `difficulty`, `type` | Returns a list of trivia questions matching the given filters |

## Local development

**Backend + full build:**
```bash
cd kuriositaeten-api
mvn clean install
```
This builds the React frontend, copies it into the WAR, and packages everything as `kuriositaeten-react.war` in `kuriositaeten-api/target/`. Deploy that WAR to a running Tomcat instance (e.g., via the Tomcat Manager app).

**Frontend only (fast iteration with hot reload):**
```bash
cd kuriositaeten-react
npm install
npm run dev
```
The Vite dev server proxies `/api/*` requests to a locally running backend (see `vite.config.js`).

> **Note:** `npm run build` inside `kuriositaeten-react/` on its own only rebuilds the frontend's `dist/` folder — it does **not** update the deployed WAR. Always run `mvn clean install` from `kuriositaeten-api/` to produce a WAR that reflects the current frontend code.

## Deployment

The app is deployed as a single WAR to Apache Tomcat, served from a subpath context (e.g., `/kuriositaeten-react/`). Because of this, Vite's `base` config must match the deployed context path so built asset URLs resolve correctly:

```js
// vite.config.js
export default defineConfig({
  base: '/kuriositaeten-react/',
  // ...
})
```

The frontend's API base URL automatically adapts between development and production:
```js
const BASE_URL = import.meta.env.DEV ? '/api' : import.meta.env.BASE_URL
```

## Known limitations

- Open Trivia DB enforces a rate limit of roughly one request per 5 seconds per IP. The backend retries automatically on a 429 response, but heavy concurrent usage may still occasionally hit the limit.

## Related projects

- [KuriositaetenVue](https://github.com/jaswpratt/KuriositaetenVue) — the same trivia app built with Vue instead of React

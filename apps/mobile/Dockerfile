FROM node:22-bookworm
WORKDIR /workspace
EXPOSE 4200
CMD ["sh", "-c", "if [ ! -x node_modules/.bin/nx ]; then npm ci; fi && exec npx nx serve mobile"]

<template>
  <v-app dark>
    <v-content fluid>
      <v-toolbar app>
        <v-toolbar-title class="headline text-uppercase">
          <span>Markdown to pdf</span>
        </v-toolbar-title>
        <v-spacer></v-spacer>
        <v-btn
          round
          outline
        >
          <span class="mr-2">Log in</span>
        </v-btn>
        <v-btn
          round
          outline
        >
          <span class="mr-2">Sign up</span>
        </v-btn>
      </v-toolbar>
      <upload-component></upload-component>
    </v-content>
  </v-app>
</template>

<script>

import UploadComponent from './components/UploadComponent.vue';

export default {
  name: 'App',
  components: {
    UploadComponent,
  },
  data() {
    return {
      //
    };
  },
};
</script>

<style>
  html {
    overflow-y: auto;
  }
</style>

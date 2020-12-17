<template>
    <footer>
        <a href="/">Codeforces</a> &copy; 2020 by Mike Mirzayanov
      <p>
          Posts: {{ postCount }}
          Users: {{ userCount }}
      </p>
    </footer>
</template>

<script>
export default {
    name: "Footer",
    props: ["postCount", "userCount"]
}
</script>

<style scoped>

</style>
const colors = require('tailwindcss/colors');
const defaultTheme = require('tailwindcss/defaultTheme');

const isProduction = !process.env.ROLLUP_WATCH; // or some other env var like NODE_ENV

module.exports = {
  enabled: isProduction,
  purge: {
    enabled: isProduction,
    content: ['./src/**/*.svelte'],
  },
  theme: {
    fontFamily: {
      sans: ['Inter var', ...defaultTheme.fontFamily.sans],
    },
    colors: {
      ...colors,
      // primary: colors.indigo,
      // secondary: colors.gray,
    },
    extend: {
      width: {
        64: '14rem',
        76: '19rem',
      },
      padding: {
        64: '14rem',
        76: '19rem',
      },
    },
  },
  variants: {
    extend: {
      ringWidth: ['hover'],
      backgroundColor: ['checked'],
      borderColor: ['checked'],
      inset: ['checked'],
      zIndex: ['hover', 'active'],
    },
  },
  darkMode: 'class',
};

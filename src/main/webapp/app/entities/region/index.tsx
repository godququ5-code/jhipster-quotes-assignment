import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Region from './region';
import RegionDeleteDialog from './region-delete-dialog';
import RegionDetail from './region-detail';
import RegionUpdate from './region-update';

const RegionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Region />} />
    <Route path="new" element={<RegionUpdate />} />
    <Route path=":id">
      <Route index element={<RegionDetail />} />
      <Route path="edit" element={<RegionUpdate />} />
      <Route path="delete" element={<RegionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RegionRoutes;

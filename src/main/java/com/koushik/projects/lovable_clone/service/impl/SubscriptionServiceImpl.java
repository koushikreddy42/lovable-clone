package com.koushik.projects.lovable_clone.service.impl;

import com.koushik.projects.lovable_clone.dto.subscription.CheckoutRequest;
import com.koushik.projects.lovable_clone.dto.subscription.CheckoutResponse;
import com.koushik.projects.lovable_clone.dto.subscription.PortalResponse;
import com.koushik.projects.lovable_clone.dto.subscription.SubscriptionResponse;
import com.koushik.projects.lovable_clone.service.SubscriptionService;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Override
    public SubscriptionResponse getCurrentSubscription(Long userId) {
        return null;
    }
}
